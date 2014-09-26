package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Prototype Portal")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class PrototypePortal extends Card
{
	public static final class PrototypePortalAbility0 extends EventTriggeredAbility
	{
		public PrototypePortalAbility0(GameState state)
		{
			super(state, "Imprint \u2014 When Prototype Portal enters the battlefield, you may exile an artifact card from your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator stuff = Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(HandOf.instance(You.instance())));
			EventFactory exile = exile(You.instance(), stuff, 1, "Exile an artifact card from your hand");
			exile.setLink(this);
			this.addEffect(youMay(exile, "You may exile an artifact card from your hand."));

			this.getLinkManager().addLinkClass(PrototypePortalAbility1.class);
		}
	}

	public static final class PrototypePortalAbility1 extends ActivatedAbility
	{
		public PrototypePortalAbility1(GameState state)
		{
			super(state, "(X), (T): Put a token that's a copy of the exiled card onto the battlefield. X is the converted mana cost of that card.");
			this.getLinkManager().addLinkClass(PrototypePortalAbility0.class);

			this.setManaCost(new ManaPool("(X)"));
			this.costsTap = true;

			SetGenerator imprintedCard = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventFactory makeCopy = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of the exiled card onto the battlefield.");
			makeCopy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeCopy.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			makeCopy.parameters.put(EventType.Parameter.OBJECT, imprintedCard);
			this.addEffect(makeCopy);

			// X is the converted mana cost of that card.
			this.defineX(ConvertedManaCostOf.instance(imprintedCard));
		}
	}

	public PrototypePortal(GameState state)
	{
		super(state);

		// Imprint \u2014 When Prototype Portal enters the battlefield, you may
		// exile an artifact card from your hand.
		this.addAbility(new PrototypePortalAbility0(state));

		// (X), (T): Put a token that's a copy of the exiled card onto the
		// battlefield. X is the converted mana cost of that card.
		this.addAbility(new PrototypePortalAbility1(state));
	}
}
