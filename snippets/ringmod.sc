s.waitForBoot({

	var win, defaultFont, synth, wobbler, gw, srout, delay, bt;

	defaultFont = Font("Verdana", 16, bold: true);

	// Main window
//	Window.closeAll;
//	gw = XGrowWindow.new();
//	gw.addXKnob(20, 20000, 200, 'exp', defaultFont, 440, "Carrier Frequency", {arg a; synth.set(\carrFreq, a);});
//	gw.addXKnob(0.5, 5000, 200, 'exp', defaultFont, 2, "Modulator Freq", {arg a; synth.set(\modFreq, a);});
//	gw.addXKnob(0, 10, 200, 'lin', defaultFont, 1, "Wobble", {arg a; synth.set(\wobbleFreq, a);});
//	gw.addXKnob(0, 10, 200, 'lin', defaultFont, 1, "Wobble Amp", {arg a; synth.set(\wobbleAmp, a);});
//	gw.createWin("Ring Modulator", 100, 400, Color.yellow(0.8), 0.9, s);

	srout = {
		SynthDef("ring-mod", {arg carrFreq = 440, modFreq = 2, wobbleFreq = 1, wobbleAmp = 1, amp = 0.06, which = 0;
			var carrier, modulator, wobbler, carriermod;
			wobbler = SinOsc.ar(Lag.kr(wobbleFreq), 0, wobbleAmp);
			carrier = SinOsc.ar(Lag.kr(carrFreq) + (5*wobbler));

			modulator = SinOsc.ar(Lag.kr(modFreq));

			Out.ar(0, carrier * modulator * amp);
		}).add;

		SynthDef("back-tone", {arg carrFreq = 220, amp = 0.06;
			Out.ar(0, SinOsc.ar(Lag.kr(carrFreq), 0, amp));
		}).add;

		s.sync;


		synth = Synth("ring-mod");
		bt = Synth("back-tone");

	   synth.set(\modFreq, 33.9);
	   synth.set(\wobbleFreq, 0.69);
	   synth.set(\wobbleAmp, 2.8);


	};

	srout.fork;

//	3.wait;

//	"done waiting".postln;

	// A440 is midi 69

//	synth.set(\carrFreq, 70.midicps);

	delay = 4*(1.0/0.69);

	while({true;}, {
		delay.wait;
		synth.set(\carrFreq, 74.midicps);
		synth.set(\modFreq, 0.1*74.midicps);
		delay.wait;
		synth.set(\carrFreq, 69.midicps);
		synth.set(\modFreq, 0.1*69.midicps);
	});


//	CmdPeriod.doOnce({win.close});

}); // end of waitForBoot

//s.freeAll

