package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Akroma, Angel of Fury")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5RRR")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class AkromaAngelofFury extends Card
{
	public AkromaAngelofFury(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Akroma, Angel of Fury can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Flying, trample, protection from white and from blue
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.WHITE, Color.BLUE), "white and from blue"));

		// (R): Akroma, Angel of Fury gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));

		// Morph (3)(R)(R)(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(R)(R)(R)"));
	}
}
