package simulator.launcher;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.EpsilonEqualStatesBuilder;
import simulator.factories.Factory;
import simulator.factories.MassEqualStatesBuilder;
import simulator.factories.MassLosingBodyBuilder;
import simulator.factories.MovingTowardsFixedPointBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoForceBuilder;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

import javax.swing.*;


public class Main {

    private enum ExecMode {
        BATCH("batch", "Batch mode"), GUI("gui", "Graphical User Interface mode");

        private String _modeTag;
        private String _modeDesc;

        private ExecMode(String modeTag, String modeDesc) {
            _modeTag = modeTag;
            _modeDesc = modeDesc;
        }

        private String getModeTag() {
            return _modeTag;
        }


    }

    // default values for some parameters
    //
    //private static ExecMode _defaultMode = ExecMode.BATCH;
    private final static Double _dtimeDefaultValue = 2500.0;
    private final static String _forceLawsDefaultValue = "nlug";
    private final static String _stateComparatorDefaultValue = "epseq";
    private final static Integer _stepsDefaultValue = 150;

    // some attributes to stores values corresponding to command-line parameters
    //_steps
    private static Integer _steps = null;
    private static Double _dtime = null;
    private static String _inFile = null;
    private static String _outFile = null;
    private static String _expOutFile = null;
    private static JSONObject _forceLawsInfo = null;
    private static JSONObject _stateComparatorInfo = null;

    // factories
    private static Factory<Body> _bodyFactory = null;
    private static Factory<ForceLaws> _forceLawsFactory = null;
    private static Factory<StateComparator> _stateComparatorFactory = null;

    private static void init() {
        // TODO initialize the bodies factory

        ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
        bodyBuilders.add(new BasicBodyBuilder());
        bodyBuilders.add(new MassLosingBodyBuilder());
        _bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

        // TODO initialize the force laws factory

        ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<>();
        forceBuilders.add(new NewtonUniversalGravitationBuilder());
        forceBuilders.add(new MovingTowardsFixedPointBuilder());
        forceBuilders.add(new NoForceBuilder());
        _forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceBuilders);


        // TODO initialize the state comparator

        ArrayList<Builder<StateComparator>> stateBuilders = new ArrayList<>();
        stateBuilders.add(new EpsilonEqualStatesBuilder());
        stateBuilders.add(new MassEqualStatesBuilder());
        _stateComparatorFactory = new BuilderBasedFactory<StateComparator>(stateBuilders);
    }

    private static void parseArgs(String[] args) throws Exception {

        // define the valid command line options
        //
        Options cmdLineOptions = buildOptions();

        // parse the command line as provided in args
        //
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(cmdLineOptions, args);

            parseHelpOption(line, cmdLineOptions);
            parseInFileOption(line);
            // TODO add support of -o, -eo, and -s (define corresponding parse methods)

            parseDeltaTimeOption(line);
            parseForceLawsOption(line);
            parseStateComparatorOption(line);

            //nuevo
            parseOutFileOption(line);
            parseStepsOption(line);
            parseExpOutFileOption(line);
            parseModeOption(line);

            // if there are some remaining arguments, then something wrong is
            // provided in the command line!
            //
            String[] remaining = line.getArgs();
            if (remaining.length > 0) {
                String error = "Illegal arguments:";
                for (String o : remaining)
                    error += (" " + o);
                throw new ParseException(error);
            }

        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }

    }

    private static Options buildOptions() {
        Options cmdLineOptions = new Options();

        // help
        cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

        // input file
        cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

        // output file
        cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Bodies JSON output file.").build());

        // steps
        cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("steps" + _stepsDefaultValue + ".").build());

        // eo
        cmdLineOptions.addOption(Option.builder("eo").longOpt("expOutFile").hasArg().desc("expOutFile.").build());

        // m

        cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution Mode. Possible values: 'batch'\n" +
                "(Batch mode), 'gui' (Graphical User\n" +
                "Interface mode). Default value: 'batch'.").build());

        // TODO add support for -o, -eo, and -s (add corresponding information to
        // cmdLineOptions)

        // delta-time
        cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
                .desc("A double representing actual time, in seconds, per simulation step. Default value: "
                        + _dtimeDefaultValue + ".")
                .build());

        // force laws
        cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
                .desc("Force laws to be used in the simulator. Possible values: "
                        + factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
                        + "'.")
                .build());

        // gravity laws
        cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
                .desc("State comparator to be used when comparing states. Possible values: "
                        + factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
                        + _stateComparatorDefaultValue + "'.")
                .build());

        return cmdLineOptions;
    }

    public static String factoryPossibleValues(Factory<?> factory) {
        if (factory == null)
            return "No values found (the factory is null)";

        String s = " ";

        for (JSONObject fe : factory.getInfo()) {
            if (s.length() > 0) {
                s = s + ", ";
            }
            s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
        }

        s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
        return s;
    }

    private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
        if (line.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
            System.exit(0);
        }
    }

    private static void parseInFileOption(CommandLine line) throws ParseException {
        _inFile = line.getOptionValue("i");

    }

    private static void parseOutFileOption(CommandLine line) throws ParseException {
        _outFile = line.getOptionValue("o");
    }

    private static void parseExpOutFileOption(CommandLine line) throws ParseException {
        _expOutFile = line.getOptionValue("eo");

    }

    private static void parseStepsOption(CommandLine line) throws ParseException {
        String st = line.getOptionValue("s", _stepsDefaultValue.toString());
        try {
            _steps = Integer.parseInt(st);
            assert (_steps > 0);
        } catch (Exception e) {
            throw new ParseException("Invalid steps value: " + st);
        }
    }


    //nuevo
    private static void parseModeOption(CommandLine line) throws Exception {

        ExecMode batch = ExecMode.BATCH;
        ExecMode gui = ExecMode.GUI;

        if (line.getOptionValue("m") == null)
            startBatchMode();
        else {
            if (line.getOptionValue("m").equals(batch.getModeTag())) {
                startBatchMode();
            } else if (line.getOptionValue("m").equals(gui.getModeTag())) {
                startGUIMode();
            }
            if (!line.getOptionValue("m").equals(batch.getModeTag()) && !line.getOptionValue("m").equals(gui.getModeTag()))
                throw new ParseException("a mode is required");
        }
    }


    private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
        String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
        try {
            _dtime = Double.parseDouble(dt);
            assert (_dtime > 0);
        } catch (Exception e) {
            throw new ParseException("Invalid delta-time value: " + dt);
        }
    }


    private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

        // the value of v is either a tag for the type, or a tag:data where data is a
        // JSON structure corresponding to the data of that type. We split this
        // information
        // into variables 'type' and 'data'
        //
        int i = v.indexOf(":");
        String type = null;
        String data = null;
        if (i != -1) {
            type = v.substring(0, i);
            data = v.substring(i + 1);
        } else {
            type = v;
            data = "{}";
        }

        // look if the type is supported by the factory
        // mira si el tipo es compatible con la fábrica
        boolean found = false;
        for (JSONObject fe : factory.getInfo()) {
            if (type.equals(fe.getString("type"))) {
                found = true;
                break;
            }
        }

        // build a corresponding JSON for that data, if found
        // construir un JSON correspondiente para esos datos, si se encuentra
        JSONObject jo = null;
        if (found) {
            jo = new JSONObject();
            jo.put("type", type);
            jo.put("data", new JSONObject(data));
        }
        return jo;

    }

    private static void parseForceLawsOption(CommandLine line) throws ParseException {
        String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
        _forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
        if (_forceLawsInfo == null) {
            throw new ParseException("Invalid force laws: " + fl);
        }
    }

    private static void parseStateComparatorOption(CommandLine line) throws ParseException {
        String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
        _stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
        if (_stateComparatorInfo == null) {
            throw new ParseException("Invalid state comparator: " + scmp);
        }
    }

    private static void startBatchMode() throws Exception {
        // TODO complete this method

        if (_inFile == null) {
            throw new ParseException("In batch mode an input file of bodies is required");
        } else {

            InputStream is = new FileInputStream(new File(_inFile));
            OutputStream os = _outFile == null ?
                    System.out : new FileOutputStream(new File(_outFile));

            PhysicsSimulator ps = new PhysicsSimulator(_forceLawsFactory.createInstance(_forceLawsInfo), _dtime);
            Controller co = new Controller(ps, _bodyFactory, _forceLawsFactory);

            InputStream expOut = null;

            StateComparator stateCmp = null;
            if (_expOutFile != null) {
                expOut = new FileInputStream(new File(_expOutFile));
                stateCmp = _stateComparatorFactory.createInstance(_stateComparatorInfo);
            }

            co.loadBodies(is);
            co.run(_steps, os, expOut, stateCmp);
        }
    }

    private static void startGUIMode() throws FileNotFoundException, InvocationTargetException, InterruptedException {

        ForceLaws fLaws = _forceLawsFactory.createInstance(_forceLawsInfo);
        PhysicsSimulator pSim = new PhysicsSimulator(fLaws, _dtime);
        Controller ctrl = new Controller(pSim, _bodyFactory, _forceLawsFactory);

        if (_inFile != null) {
            InputStream input = new FileInputStream(new File(_inFile));
            try {

                ctrl.loadBodies(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                new MainWindow(ctrl);
            }
        });
    }


    private static void start(String[] args) throws Exception {
        parseArgs(args);
    }

    public static void main(String[] args) {
        try {
            init();
            start(args);
        } catch (Exception e) {
            System.err.println("Something went wrong ...");
            System.err.println();
            e.printStackTrace();
        }
    }
}
