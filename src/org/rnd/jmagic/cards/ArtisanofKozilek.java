package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Artisan of Kozilek")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(9)")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ArtisanofKozilek extends Card
{
	public static final class WhenYouCastRaiseDead extends EventTriggeredAbility
	{
		public WhenYouCastRaiseDead(GameState state)
		{
			super(state, "When you cast Artisan of Kozilek, you may return target creature card from your graveyard to the battlefield.");
			this.triggersFromStack();

			this.addPattern(whenYouCastThisSpell());

			SetGenerator creatureCards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());

			Target target = this.addTarget(Intersect.instance(creatureCards, InZone.instance(yourGraveyard)), "target creature card from your graveyard");

			EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to the battlefield.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(youMay(factory, "You may return target creature card from your graveyard to the battlefield."));
		}
	}

	public ArtisanofKozilek(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(9);

		// When you cast Artisan of Kozilek, you may return target creature card
		// from your graveyard to the battlefield.
		this.addAbility(new WhenYouCastRaiseDead(state));

		// Annihilator 2
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 2));
	}
}
