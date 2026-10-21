import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Test client.
//   java Client          runs the tests of the three chains
//   java Client 1|2|3    runs only the test of that chain
//   java Client -i       interactive mode: enter codes for a door with chain1
public class Client {
    static final String openCode = "1111";
    static final String fireAlarmCode = "2222";
    static final String unlockCode = "3333";

    // all 5 capabilities. Order matters: Log first (logs everything),
    // Lock last (only receives the codes nobody has served = failed trials)
    static CodeHandler buildChain1() {
        return new Log(
                new Unlock(unlockCode,
                        new FireAlarm(fireAlarmCode,
                                new Open(openCode,
                                        new Lock(null)))));
    }

    // just log + open
    static CodeHandler buildChain2() {
        return new Log(new Open(openCode, null));
    }

    // log + fire alarm + open, never gets locked
    static CodeHandler buildChain3() {
        return new Log(
                new FireAlarm(fireAlarmCode,
                        new Open(openCode, null)));
    }

    static void title(String s) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(s);
        System.out.println("==================================================");
    }

    static void testChain1() {
        title("CHAIN 1: Log -> Unlock -> FireAlarm -> Open -> Lock");
        Door d1 = new Door("d1", buildChain1());
        d1.processCode("1111"); // opens
        d1.processCode("2222"); // opens and fires alarm
        d1.processCode("1234"); // first trial
        d1.processCode("4321"); // second trial
        d1.processCode("5555"); // third trial, gets locked
        d1.processCode("6666"); // invalid unlock code
        d1.processCode("7777"); // invalid unlock code
        d1.processCode("1111"); // open code, but door is locked: invalid unlock code
        d1.processCode("3333"); // valid unlock code, now can be opened or fire alarm
        d1.processCode("2222"); // opens and fires alarm
        d1.processCode("9999"); // first trial
        d1.processCode("1111"); // opens, resets the number of trials
        d1.processCode("3333"); // unlock code when not locked: just a failed trial

        title("CHAIN 1 -> CHAIN 2: change behaviour of door d1 at run time");
        d1.setCodeHandler(buildChain2());
        d1.processCode("2222"); // fire alarm code no longer recognized
        d1.processCode("1111"); // opens
    }

    static void testChain2() {
        title("CHAIN 2: Log -> Open");
        Door d2 = new Door("d2", buildChain2());
        d2.processCode("1111"); // opens
        d2.processCode("1234"); // wrong code, nothing happens
        d2.processCode("4321"); // wrong code, nothing happens
        d2.processCode("5555"); // wrong code, does NOT get locked
        d2.processCode("6666"); // wrong code, does NOT get locked
        d2.processCode("2222"); // fire alarm code is not recognized
        d2.processCode("3333"); // unlock code is not recognized
        d2.processCode("1111"); // still opens
    }

    static void testChain3() {
        title("CHAIN 3: Log -> FireAlarm -> Open");
        Door d3 = new Door("d3", buildChain3());
        d3.processCode("1111"); // opens
        d3.processCode("2222"); // opens and fires alarm
        d3.processCode("1234"); // wrong code
        d3.processCode("4321"); // wrong code
        d3.processCode("5555"); // wrong code, does NOT get locked
        d3.processCode("6666"); // wrong code
        d3.processCode("3333"); // unlock code is not recognized
        d3.processCode("2222"); // opens and fires alarm
        d3.processCode("1111"); // opens
    }

    static void interactive() throws IOException {
        Door d = new Door("d1", buildChain1());
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Input code (q to quit): ");
            String inputCode = stdin.readLine();
            if (inputCode == null || inputCode.equals("q")) {
                break;
            }
            d.processCode(inputCode.trim());
        }
    }

    public static void main(String[] args) throws IOException {
        String option = args.length > 0 ? args[0] : "all";
        switch (option) {
            case "1": testChain1(); break;
            case "2": testChain2(); break;
            case "3": testChain3(); break;
            case "-i": interactive(); break;
            default:
                testChain1();
                testChain2();
                testChain3();
        }
    }
}
