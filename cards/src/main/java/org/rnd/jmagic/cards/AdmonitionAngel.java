package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Admonition Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WWW")
@ColorIdentity({Color.WHITE})
public final class AdmonitionAngel extends Card
{
	public static final class TheAngelTakethAway extends EventTriggeredAbility
	{
		public TheAngelTakethAway(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, you may exile target nonland permanent other than Admonition Angel.");
			this.addPattern(landfall());

			SetGenerator nonLandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			Target target = this.addTarget(RelativeComplement.instance(nonLandPermanents, ABILITY_SOURCE_OF_THIS), "target nonland permanent other than Admonition Angel");

			EventFactory exile = exile(targetedBy(target), "Exile another target nonland permanent.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(TheAngelGivethBack.class);
		}
	}

	public static final class TheAngelGivethBack extends EventTriggeredAbility
	{
		public TheAngelGivethBack(GameState state)
		{
			super(state, "When Admonition Angel leaves the battlefield, return all cards exiled with it to the battlefield under their owners' control.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator exiledCards = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory returnCard = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return all cards exiled with it to the battlefield under their owners' control.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.OBJECT, exiledCards);
			this.addEffect(returnCard);

			this.getLinkManager().addLinkClass(TheAngelTakethAway.class);
		}
	}

	public AdmonitionAngel(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may exile target nonland permanent other than Admonition
		// Angel.
		this.addAbility(new TheAngelTakethAway(state));

		// When Admonition Angel leaves the battlefield, return all cards exiled
		// with it to the battlefield under their owners' control.
		this.addAbility(new TheAngelGivethBack(state));
	}
}
