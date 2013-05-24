package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quicksilver Amulet")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.RARE)})
@ColorIdentity({})
public final class QuicksilverAmulet extends Card
{
	public static final class QuicksilverAmuletAbility0 extends ActivatedAbility
	{
		public QuicksilverAmuletAbility0(GameState state)
		{
			super(state, "(4), (T): You may put a creature card from your hand onto the battlefield.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(inYourHand, HasType.instance(Type.CREATURE)));

			this.addEffect(youMay(putOntoBattlefield, "You may put a creature card from your hand onto the battlefield."));

		}
	}

	public QuicksilverAmulet(GameState state)
	{
		super(state);

		// (4), (T): You may put a creature card from your hand onto the
		// battlefield.
		this.addAbility(new QuicksilverAmuletAbility0(state));
	}
}
