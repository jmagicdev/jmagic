package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Firehoof Cavalry")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.RED, Color.WHITE})
public final class FirehoofCavalry extends Card
{
	public static final class FirehoofCavalryAbility0 extends ActivatedAbility
	{
		public FirehoofCavalryAbility0(GameState state)
		{
			super(state, "(3)(R): Firehoof Cavalry gets +2/+0 and gains trample until end of turn.");
			this.setManaCost(new ManaPool("(3)(R)"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Firehoof Cavalry gets +2/+0 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public FirehoofCavalry(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (3)(R): Firehoof Cavalry gets +2/+0 and gains trample until end of
		// turn.
		this.addAbility(new FirehoofCavalryAbility0(state));
	}
}
