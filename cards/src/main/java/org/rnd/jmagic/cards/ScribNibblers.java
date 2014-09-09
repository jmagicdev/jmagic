package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrib Nibblers")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class ScribNibblers extends Card
{
	public static final class ScribNibblersAbility0 extends ActivatedAbility
	{
		public ScribNibblersAbility0(GameState state)
		{
			super(state, "(T): Exile the top card of target player's library. If it's a land card, you gain 1 life.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory exile = exile(TopCards.instance(1, LibraryOf.instance(target)), "Exile the top card of target player's library.");
			this.addEffect(exile);

			EventFactory ifThen = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a land card, you gain 1 life.");
			ifThen.parameters.put(EventType.Parameter.IF, Intersect.instance(NewObjectOf.instance(EffectResult.instance(exile)), HasType.instance(Type.LAND)));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(gainLife(You.instance(), 1, "You gain 1 life.")));
			this.addEffect(ifThen);
		}
	}

	public static final class ScribNibblersAbility1 extends EventTriggeredAbility
	{
		public ScribNibblersAbility1(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may untap Scrib Nibblers.");
			this.addPattern(landfall());
			this.addEffect(youMay(untap(ABILITY_SOURCE_OF_THIS, "Untap Scrib Nibblers."), "You may untap Scrib Nibblers."));
		}
	}

	public ScribNibblers(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Exile the top card of target player's library. If it's a land
		// card, you gain 1 life.
		this.addAbility(new ScribNibblersAbility0(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may untap Scrib Nibblers.
		this.addAbility(new ScribNibblersAbility1(state));
	}
}
