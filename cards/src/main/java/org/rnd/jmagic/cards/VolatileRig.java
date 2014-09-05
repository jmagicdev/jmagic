package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Volatile Rig")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class VolatileRig extends Card
{
	public static final class VolatileRigAbility2 extends EventTriggeredAbility
	{
		public VolatileRigAbility2(GameState state)
		{
			super(state, "Whenever Volatile Rig is dealt damage, flip a coin. If you lose the flip, sacrifice Volatile Rig.");
			this.addPattern(whenThisIsDealtDamage());

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(flip);

			SetGenerator lose = Intersect.instance(Identity.instance(Answer.LOSE), EffectResult.instance(flip));
			this.addEffect(ifThen(lose, sacrificeThis("Volatile Rig"), "If you lose the flip, sacrifice Volatile Rig."));
		}
	}

	public static final class VolatileRigAbility3 extends EventTriggeredAbility
	{
		public VolatileRigAbility3(GameState state)
		{
			super(state, "When Volatile Rig dies, flip a coin. If you lose the flip, it deals 4 damage to each creature and each player.");
			this.addPattern(whenThisDies());

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(flip);

			SetGenerator lose = Intersect.instance(Identity.instance(Answer.LOSE), EffectResult.instance(flip));
			EventFactory damage = permanentDealDamage(4, CREATURES_AND_PLAYERS, "It deals 4 damage to each creature and each player");
			this.addEffect(ifThen(lose, damage, "If you lose the flip, it deals 4 damage to each creature and each player."));
		}
	}

	public VolatileRig(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Volatile Rig attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, "Volatile Rig"));

		// Whenever Volatile Rig is dealt damage, flip a coin. If you lose the
		// flip, sacrifice Volatile Rig.
		this.addAbility(new VolatileRigAbility2(state));

		// When Volatile Rig dies, flip a coin. If you lose the flip, it deals 4
		// damage to each creature and each player.
		this.addAbility(new VolatileRigAbility3(state));
	}
}
