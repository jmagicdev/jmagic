package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Galvanoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Galvanoth extends Card
{
	public static final class GalvanothAbility0 extends EventTriggeredAbility
	{
		public GalvanothAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top card of your library. If it's an instant or sorcery card, you may cast it without paying its mana cost.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory lookFactory = new EventFactory(EventType.LOOK, "Look at the top card of your library.");
			lookFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			lookFactory.parameters.put(EventType.Parameter.OBJECT, topCard);
			lookFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory effect = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "Cast it without paying its mana cost.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory ifSpell = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's an instant or sorcery card, you may cast it without paying its mana cost.");
			ifSpell.parameters.put(EventType.Parameter.IF, Intersect.instance(topCard, HasType.instance(Type.INSTANT, Type.SORCERY)));
			ifSpell.parameters.put(EventType.Parameter.THEN, Identity.instance(youMay(effect, "You may cast it without paying its mana cost.")));

			EventFactory mayLook = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may look at the top card of your library. If it's an instant or sorcery card, you may cast it without paying its mana cost.");
			mayLook.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(lookFactory, "You may look at the top card of your library.")));
			mayLook.parameters.put(EventType.Parameter.THEN, Identity.instance(ifSpell));
			this.addEffect(mayLook);
		}
	}

	public Galvanoth(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// At the beginning of your upkeep, you may look at the top card of your
		// library. If it's an instant or sorcery card, you may cast it without
		// paying its mana cost.
		this.addAbility(new GalvanothAbility0(state));
	}
}
