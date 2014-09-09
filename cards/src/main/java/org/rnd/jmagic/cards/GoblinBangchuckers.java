package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Bangchuckers")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class GoblinBangchuckers extends Card
{
	public static final class GoblinBangchuckersAbility0 extends ActivatedAbility
	{
		public GoblinBangchuckersAbility0(GameState state)
		{
			super(state, "(T): Flip a coin. If you win the flip, Goblin Bangchuckers deals 2 damage to target creature or player. If you lose the flip, Goblin Bangchuckers deals 2 damage to itself.");
			this.costsTap = true;

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin.");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			EventFactory hit = permanentDealDamage(2, target, "Goblin Bangchuckers deals 2 damage to target creature or player");

			EventFactory criticalMiss = permanentDealDamage(2, ABILITY_SOURCE_OF_THIS, "Goblin Bangchuckers deals 2 damage to itself");

			this.addEffect(ifThenElse(flip, hit, criticalMiss, "Flip a coin. If you win the flip, Goblin Bangchuckers deals 2 damage to target creature or player. If you lose the flip, Goblin Bangchuckers deals 2 damage to itself."));
		}
	}

	public GoblinBangchuckers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Flip a coin. If you win the flip, Goblin Bangchuckers deals 2
		// damage to target creature or player. If you lose the flip, Goblin
		// Bangchuckers deals 2 damage to itself.
		this.addAbility(new GoblinBangchuckersAbility0(state));
	}
}
