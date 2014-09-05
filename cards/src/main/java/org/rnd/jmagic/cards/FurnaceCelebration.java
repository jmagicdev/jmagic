package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Furnace Celebration")
@Types({Type.ENCHANTMENT})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FurnaceCelebration extends Card
{
	public static final class FurnaceCelebrationAbility0 extends EventTriggeredAbility
	{
		public FurnaceCelebrationAbility0(GameState state)
		{
			super(state, "Whenever you sacrifice another permanent, you may pay (2). If you do, Furnace Celebration deals 2 damage to target creature or player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.PERMANENT, RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2).");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2). If you do, Furnace Celebration deals 2 damage to target creature or player.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(permanentDealDamage(2, target, "Furnace Celebration deals 2 damage to target creature or player.")));
			this.addEffect(factory);
		}
	}

	public FurnaceCelebration(GameState state)
	{
		super(state);

		// Whenever you sacrifice another permanent, you may pay (2). If you do,
		// Furnace Celebration deals 2 damage to target creature or player.
		this.addAbility(new FurnaceCelebrationAbility0(state));
	}
}
