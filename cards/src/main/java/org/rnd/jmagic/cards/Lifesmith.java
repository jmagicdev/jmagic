package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lifesmith")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Lifesmith extends Card
{
	public static final class LifesmithAbility0 extends EventTriggeredAbility
	{
		public LifesmithAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may pay (1). If you do, you gain 3 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (1)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (1). If you do, you gain 3 life.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(gainLife(You.instance(), 3, "You gain 3 life.")));
			this.addEffect(effect);
		}
	}

	public Lifesmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an artifact spell, you may pay (1). If you do, you
		// gain 3 life.
		this.addAbility(new LifesmithAbility0(state));
	}
}
