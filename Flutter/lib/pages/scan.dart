import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:ratemybite/assets/elements/search_bar.dart';

class ScanPage extends StatelessWidget {
  const ScanPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.surface,
      appBar: AppBar(
        title: SvgPicture.asset(
          'lib/assets/icons/top_bar/logo.svg',
        )
      ),
      body: Center(
        child: MySearchBar()
      ),
    );
  }
}
