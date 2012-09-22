package com.ivstuart.tmud.constants;

public class DamageConstants {

	// Your armor protects you from a <T-NAME> attack

	private static final String message[] = {
			"<S-You/NAME> barely attack<S-/s> <T-you/NAME>",
			"<S-You/NAME> barely attack<S-/s> <T-you/NAME>",
			"<S-You/NAME> attack<S-/s> <T-you/NAME>",
			"<S-You/NAME> attack<S-/s> <T-you/NAME> hard",
			"<S-You/NAME> attack<S-/s> <T-you/NAME> very hard",
			"<S-You/NAME> attack<S-/s> <T-you/NAME> extremely hard",
			"<S-You/NAME> attack<S-/s> <T-you/NAME> with great force",
			"<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful attack",
			"<S-You/NAME> cause<S-/s> <T-you/NAME> to cringe in pain with <S-your/GEN-his> forceful attack!",
			"<S-You/NAME> cause<S-/s> <T-you/NAME> to cry out in agony with <S-your/GEN-his> powerful attack!",
			"<S-You/NAME> $Bmaim<S-/s>$J <T-you/NAME> with <S-your/GEN-his> skillful attack!",
			"<S-You/NAME> $Hshred<S-/s>$J <T-you/NAME> with <S-your/GEN-his> dexterous attack!",
			"<S-You/NAME> $Gmangle<S-/s>$J <T-you/NAME> with <S-your/GEN-his> stunning attack!",
			"<S-You/NAME> $AMuTiLaTe<S-/S>$J <T-you/NAME> with <S-your/GEN-his> masterful attack!",
			"<S-You/NAME> $DDeCiMaTe<S-/S>$J <T-you/NAME> with <S-your/GEN-his> enchanting attack!",
			"<S-You/NAME> $KDEVASTATE<S-/S>$J <T-you/NAME> with <S-your/GEN-his> captivating attack!",
			"<S-You/NAME> <<< $BAnnihilat<S-E/eS>$J >>> <T-you/NAME> with <S-your/GEN-his> deadly attack!",
			"<S-You/NAME> >-> E$Gv$JI$Gs$JC$Ge$JR$Ga$JT$Ge$J<S-/s> <-< <T-you/NAME> with <S-your/GEN-his> amazing attack!",
			"<S-You/NAME> $K<-<--< $ALiQuIfY<S-/s>$K >-->->$J <T-you/NAME> with <S-your/GEN-his> deadly attack!",
			"<S-You/NAME> $K/$A\\$K/$A\\$K/$A\\$KE$AR$KA$AD$KI$AC$KA$AT$KE$A<S-/S>\\$K/$A\\$K/$A\\$K/$A\\$J <T-you/NAME> with <S-your/GEN-his> incredible attack!",
			"<S-You/NAME> $G((( $I( $HELIMINATE<S-/S>$G $I)$G )))$J <T-you/NAME> with <S-your/GEN-his> unsurvivable attack!",
			"<S-You/NAME> $G[-$C[-$B<< $DO$Kb$DL$Ki$DT$Ke$DR$Ka$DT$Ke$J<S-/S> $B>>$C-]$G-]$J <T-you/NAME> with <S-your/GEN-his> unsurpassed attack!",
			"<S-You/NAME> < <$E< $J<$E<$F< $JDESTORY<S-/S> $F>$E>$J> $E>$J> > <T-you/NAME> with <S-your/GEN-his> mind-boggling attack!",
			"<S-You/NAME> $G... .. .V $Da P $Jo R$D i Z$G e<S-/ S>. .. ...$J <T-you/NAME> with <S-your/GEN-his> unforgettable attack!",
			"<S-You/NAME> . $B. $J.$A > > > $BA$J-$BT$J-$BO$J-$BM$J-$BI$J-$BZ$J-$BE<S-/-S> $A< < < $J. $B.$J . <T-you/NAME> with <S-your/GEN-his> unsurvivable attack!",
			"<S-You/NAME> $G<<-<<--< $Be X t E r M i N a T e <S-/S>$G >-->>->>$J <T-you/NAME> with <S-your/GEN-his> unparalleled attack!" };

	public static String help() {
		StringBuilder sb = new StringBuilder();
		for (String element : message) {
			sb.append(element + "\n");
		}
		return sb.toString();
	}

	public static String toString(int damage) {
		int index = (int) Math.sqrt(damage);
		// int index = damage / 20;
		if (index > message.length) {
			index = message.length - 1;
		}
		return message[index];
	}

}