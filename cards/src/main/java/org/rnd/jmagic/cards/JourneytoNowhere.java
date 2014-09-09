package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Journey to Nowhere")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class JourneytoNowhere extends Card
{
	public static final class TheLordTakethAway extends EventTriggeredAbility
	{
		public TheLordTakethAway(GameState state)
		{
			super(state, "When Journey to Nowhere enters the battlefield, exile target creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventFactory exile = exile(targetedBy(target), "Exile target creature.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(TheLordGivethBack.class);
		}
	}

	public static final class TheLordGivethBack extends EventTriggeredAbility
	{
		public TheLordGivethBack(GameState state)
		{
			super(state, "When Journey to Nowhere leaves the battlefield, return the exiled card to the battlefield under its owner's control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory returnCard = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return the exiled card to the battlefield under its owner's control.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.CONTROLLER, OwnerOf.instance(exiledCard));
			returnCard.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(returnCard);

			this.getLinkManager().addLinkClass(TheLordTakethAway.class);
		}
	}

	public JourneytoNowhere(GameState state)
	{
		super(state);

		// When Journey to Nowhere enters the battlefield, exile target
		// creature.
		this.addAbility(new TheLordTakethAway(state));

		// When Journey to Nowhere leaves the battlefield, return the exiled
		// card to the battlefield under its owner's control.
		this.addAbility(new TheLordGivethBack(state));
	}
}
