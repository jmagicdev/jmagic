package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Embersmith")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Embersmith extends Card
{
	public static final class EmbersmithAbility0 extends EventTriggeredAbility
	{
		public EmbersmithAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may pay (1). If you do, Embersmith deals 1 damage to target creature or player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (1)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (1). If you do, Embersmith deals 1 damage to target creature or player.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(permanentDealDamage(1, target, "Embersmith deals 1 damage to target creature or player.")));
			this.addEffect(effect);
		}
	}

	public Embersmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an artifact spell, you may pay (1). If you do,
		// Embersmith deals 1 damage to target creature or player.
		this.addAbility(new EmbersmithAbility0(state));
	}
}
