package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.abilities.keywords.*;

@Name("Akroma's Memorial")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.MYTHIC), @Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class AkromasMemorial extends Card
{
	public static final class CreaturesYouControlAreAkroma extends StaticAbility
	{
		public CreaturesYouControlAreAkroma(GameState state)
		{
			super(state, "Creatures you control have flying, first strike, vigilance, trample, haste, and protection from black and from red.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, Flying.class, FirstStrike.class, Vigilance.class, Trample.class, Haste.class, Protection.FromBlack.class, Protection.FromRed.class));
		}
	}

	public AkromasMemorial(GameState state)
	{
		super(state);

		// Creatures you control have flying, first strike, vigilance, trample,
		// haste, and protection from black and from red.
		this.addAbility(new CreaturesYouControlAreAkroma(state));
	}
}
