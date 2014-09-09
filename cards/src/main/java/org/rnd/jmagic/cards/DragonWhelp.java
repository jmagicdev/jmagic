package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dragon Whelp")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class DragonWhelp extends Card
{
	public static final class WhelpFirebreathing extends ActivatedAbility
	{
		public WhelpFirebreathing(GameState state)
		{
			super(state, "(R): Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.");
			this.setManaCost(new ManaPool("R"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+0), "Dragon Whelp gets +1/+0 until end of turn."));
			this.addEffect(ifActivatedNOrMoreTimesSacrifice(state, 4, "Dragon Whelp"));
		}
	}

	public DragonWhelp(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new WhelpFirebreathing(state));
	}
}
