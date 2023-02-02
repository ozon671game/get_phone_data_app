import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  int batteryLevel = 0;
  bool batteryIsCharged = false;

  getData() async {
    batteryLevel = await Bridge.getBatteryLevel();
    batteryIsCharged = await Bridge.getIsCharged();
    setState(() {});
  }

  @override
  void initState() {
    getData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Center(child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text('My Battery Level = $batteryLevel'),
          Text('My Battery Is Charged = $batteryIsCharged'),
        ],
      )),
    );
  }
}


class Bridge {
  static const channel = MethodChannel('BatteryLevel');

  static Future<int> getBatteryLevel() async {
    int result = await channel.invokeMethod('getBatteryLevel');
    return result;
  }

  static Future<bool> getIsCharged() async {
    bool result = await channel.invokeMethod('getIsCharged');
    return result;
  }
}
