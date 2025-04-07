import 'package:flutter/material.dart';

class MySearchBar extends StatefulWidget {
  const MySearchBar({super.key});

  @override
  State<MySearchBar> createState() => _MySearchBarState();
}

class _MySearchBarState extends State<MySearchBar> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Align(
        alignment: Alignment.topCenter,
        child: SearchBar(
          leading: const Icon(Icons.search),
          hintText: 'Search',
          backgroundColor: WidgetStateProperty.all(
              Theme.of(context).colorScheme.primaryContainer
          ),
          shadowColor: WidgetStateProperty.all(Colors.black.withAlpha(0)),
          shape: WidgetStateProperty.all(
              RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(7.0)
              )
          ),
          padding: WidgetStateProperty.all(
              EdgeInsets.symmetric(horizontal: 10.0)
          ),
          //onChanged: (query) {/*Update suggestions(?) with each letter*/},
          //onSubmitted: /*does the search*/,
          //onTap: /*on focus*/
          //onTapOutside: /*out of focus*/,
        ),
      )
    );
  }
}
