import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numTestCase = scanner.nextInt();
        scanner.nextLine();

        for (int tc = 0; tc < numTestCase; tc++) {
            int numCars = scanner.nextInt();
            int numEvents = scanner.nextInt();
            scanner.nextLine();

            Map<String, Integer[]> cars = new HashMap<>();
            Map<String, String> state = new HashMap<>();
            Map<String, Integer> cost = new HashMap<>();

            for (int i = 0; i < numCars; i++) {
                String[] carInfo = scanner.nextLine().split(" ");
                cars.put(carInfo[0], new Integer[] {Integer.parseInt(carInfo[1]), Integer.parseInt(carInfo[2]), Integer.parseInt(carInfo[3])});
            }

            for (int i = 0; i < numEvents; i++) {
                String[] eventInfo = scanner.nextLine().split(" ");
                String spy = eventInfo[1];
                String eventType = eventInfo[2];
                String eventArg = eventInfo[3];

                if (!state.containsKey(spy)) {
                    state.put(spy, null);
                    cost.put(spy, 0);
                }

                int spyCost = cost.get(spy);

                if (spyCost == -1) {
                    continue;
                }

                if (eventType.equals("p")) {
                    if (state.get(spy) != null) {
                        cost.put(spy, -1);
                    } else {
                        state.put(spy, eventArg);
                        cost.put(spy, spyCost + cars.get(eventArg)[1]);
                    }
                } else if (eventType.equals("r")) {
                    if (state.get(spy) == null) {
                        cost.put(spy, -1);
                    } else {
                        cost.put(spy, spyCost + Integer.parseInt(eventArg) * cars.get(state.get(spy))[2]);
                        state.put(spy, null);
                    }
                } else if (eventType.equals("a")) {
                    if (state.get(spy) == null) {
                        cost.put(spy, -1);
                    } else {
                        cost.put(spy, spyCost + (int) Math.ceil(Integer.parseInt(eventArg) * cars.get(state.get(spy))[0] / 100.0));
                    }
                }
            }

            for (String spy : state.keySet()) {
                if (state.get(spy) != null) {
                    cost.put(spy, -1);
                }
            }

            List<Map.Entry<String, Integer>> costList = new ArrayList<>(cost.entrySet());
            costList.sort(Map.Entry.comparingByKey());

            for (Map.Entry<String, Integer> entry : costList) {
                System.out.print(entry.getKey() + " ");
                if (entry.getValue() == -1) {
                    System.out.println("INCONSISTENT");
                } else {
                    System.out.println(entry.getValue());
                }
            }
        }
    }
}

