package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Akroma, Angel of Wrath")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WWW")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AkromaAngelofWrath extends Card
{
	public AkromaAngelofWrath(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying, first strike, vigilance, trample, haste, protection from
		// black and from red
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.BLACK, Color.RED), "black and from red"));
	}
}
