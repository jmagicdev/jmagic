package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mimic Vat")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class MimicVat extends Card
{
	public static final class MimicVatAbility0 extends EventTriggeredAbility
	{
		public MimicVatAbility0(GameState state)
		{
			super(state, "Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), Intersect.instance(NonToken.instance(), CreaturePermanents.instance()), true));

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory exile = exile(thatCard, "Exile that card.");
			exile.setLink(this);

			EventFactory graveyard = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Return each other card exiled with Mimic Vat to its owner's graveyard.");
			graveyard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			graveyard.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(ChosenFor.instance(This.instance()), NewObjectOf.instance(EffectResult.instance(exile))));

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(exile, "You may exile that card.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(graveyard));
			this.addEffect(factory);

			this.getLinkManager().addLinkClass(MimicVatAbility1.class);
		}
	}

	public static final class MimicVatAbility1 extends ActivatedAbility
	{
		public MimicVatAbility1(GameState state)
		{
			super(state, "(3), (T): Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of the exiled card.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));
			this.addEffect(factory);

			SetGenerator it = NewObjectOf.instance(EffectResult.instance(factory));

			this.addEffect(createFloatingEffect("It gains haste.", addAbilityToObject(it, org.rnd.jmagic.abilities.keywords.Haste.class)));

			EventFactory exile = exile(delayedTriggerContext(it), "Exile it");
			EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next ends step.");
			exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
			this.addEffect(exileLater);

			this.getLinkManager().addLinkClass(MimicVatAbility0.class);
		}
	}

	public MimicVat(GameState state)
	{
		super(state);

		// Imprint \u2014 Whenever a nontoken creature is put into a graveyard
		// from the battlefield, you may exile that card. If you do, return each
		// other card exiled with Mimic Vat to its owner's graveyard.
		this.addAbility(new MimicVatAbility0(state));

		// (3), (T): Put a token onto the battlefield that's a copy of the
		// exiled card. It gains haste. Exile it at the beginning of the next
		// end step.
		this.addAbility(new MimicVatAbility1(state));
	}
}
