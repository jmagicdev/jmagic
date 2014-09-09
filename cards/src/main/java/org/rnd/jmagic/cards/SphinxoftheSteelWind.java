package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sphinx of the Steel Wind")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("5WUB")
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class SphinxoftheSteelWind extends Card
{
	public SphinxoftheSteelWind(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, org.rnd.jmagic.engine.generators.HasColor.instance(Color.RED, Color.GREEN), "red and from green"));
	}
}
