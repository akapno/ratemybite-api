import 'package:flutter/material.dart';


//DARK THEME PALETTE
const Color myMainColor = Color.fromARGB(255, 86, 179, 86);
const Color myBackground = Color.fromARGB(255, 22, 29, 29);
const Color myForeground = Color.fromARGB(255, 44, 53, 53);
const Color myItemFill = Color.fromARGB(255, 29, 38, 38);
const Color myItemFillSelected = Color.fromARGB(255, 84, 108, 108);
const Color myItem3DBorder = Color.fromARGB(89, 0, 0, 0);
const Color myGreyLetters = Color.fromARGB(128, 0, 0, 0);
const Color myWarning = Color.fromARGB(255, 255, 187, 0);
const Color myDanger = Color.fromARGB(255, 255, 55, 55);

final ThemeData lightMode = ThemeData(                  //LIGHT THEME
  brightness: Brightness.light,
  colorScheme: ColorScheme.light(
    surface: Colors.white,

  )
);

final ThemeData darkMode = ThemeData(                   //DARK THEME
  brightness: Brightness.dark,
  colorScheme: ColorScheme.dark(
    primary: myMainColor,
    secondary: myForeground,
    primaryContainer: myItemFill,
    onPrimaryContainer: myItemFillSelected,
    surface: myBackground,
    surfaceContainerLow: myItem3DBorder,
  ),
  fontFamily: 'Nunito'
);