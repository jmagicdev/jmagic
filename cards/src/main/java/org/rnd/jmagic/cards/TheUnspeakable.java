package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("The Unspeakable")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("6UUU")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TheUnspeakable extends Card
{
	public static final class MeleeReturnArcane extends EventTriggeredAbility
	{
		public MeleeReturnArcane(GameState state)
		{
			super(state, "Whenever The Unspeakable deals combat damage to a player, you may return target Arcane card from your graveyard to your hand.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			Target t = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ARCANE), InZone.instance(yourGraveyard)), "target Arcane card");

			EventFactory returnCard = new EventFactory(EventType.MOVE_OBJECTS, "Return target Arcane card from your graveyard to your hand.");
			returnCard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCard.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			returnCard.parameters.put(EventType.Parameter.OBJECT, targetedBy(t));
			this.addEffect(youMay(returnCard, "You may return target Arcane card from your graveyard to your hand."));
		}
	}

	public TheUnspeakable(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever The Unspeakable deals combat damage to a player, you may
		// return target Arcane card from your graveyard to your hand.
		this.addAbility(new MeleeReturnArcane(state));
	}
}
