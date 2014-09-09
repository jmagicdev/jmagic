package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Saberclaw Golem")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@ColorIdentity({Color.RED})
public final class SaberclawGolem extends Card
{
	public static final class SaberclawGolemAbility0 extends ActivatedAbility
	{
		public SaberclawGolemAbility0(GameState state)
		{
			super(state, "(R): Saberclaw Golem gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(createFloatingEffect("Saberclaw Golem gains first strike until end of turn.", addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class)));
		}
	}

	public SaberclawGolem(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// (R): Saberclaw Golem gains first strike until end of turn.
		this.addAbility(new SaberclawGolemAbility0(state));
	}
}
