package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Knowledge and Power")
@Types({Type.ENCHANTMENT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class KnowledgeandPower extends Card
{
	public static final class KnowledgeandPowerAbility0 extends EventTriggeredAbility
	{
		public KnowledgeandPowerAbility0(GameState state)
		{
			super(state, "Whenever you scry, you may pay (2). If you do, Knowledge and Power deals 2 damage to target creature or player.");

			SimpleEventPattern scry = new SimpleEventPattern(EventType.SCRY);
			scry.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(scry);

			EventFactory mayPay = youMayPay("(2)");

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			EventFactory effect = permanentDealDamage(2, target, "Knowledge and Power deals 2 damage to target creature or player.");

			this.addEffect(ifThen(mayPay, effect, "You may pay (2). If you do, Knowledge and Power deals 2 damage to target creature or player."));
		}
	}

	public KnowledgeandPower(GameState state)
	{
		super(state);

		// Whenever you scry, you may pay (2). If you do, Knowledge and Power
		// deals 2 damage to target creature or player.
		this.addAbility(new KnowledgeandPowerAbility0(state));
	}
}
