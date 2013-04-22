package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Restoration Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class RestorationAngel extends Card
{
	public static final class RestorationAngelAbility2 extends EventTriggeredAbility
	{
		public RestorationAngelAbility2(GameState state)
		{
			super(state, "When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control, then return that card to the battlefield under your control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.ANGEL)), "target non-Angel creature you control"));

			EventFactory factory = new EventFactory(BLINK, "Exile target non-Angel creature you control, then return that card to the battlefield under your control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, target);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(youMay(factory));
		}
	}

	public RestorationAngel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Restoration Angel enters the battlefield, you may exile target
		// non-Angel creature you control, then return that card to the
		// battlefield under your control.
		this.addAbility(new RestorationAngelAbility2(state));
	}
}
