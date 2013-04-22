package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guild Feud")
@Types({Type.ENCHANTMENT})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GuildFeud extends Card
{
	public static final class GuildFeudAbility0 extends EventTriggeredAbility
	{
		public GuildFeudAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, target opponent reveals the top three cards of his or her library, may put a creature card from among them onto the battlefield, then puts the rest into his or her graveyard. You do the same with the top three cards of your library. If two creatures are put onto the battlefield this way, those creatures fight each other.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			SetGenerator opponentTopThree = TopCards.instance(3, LibraryOf.instance(target));
			this.addEffect(reveal(opponentTopThree, "Target opponent reveals the top three cards of his or her library,"));

			SetGenerator opponentCreatureCard = Intersect.instance(HasType.instance(Type.CREATURE), opponentTopThree);
			EventFactory opponentPutCreature = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from among them onto the battlefield");
			opponentPutCreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			opponentPutCreature.parameters.put(EventType.Parameter.CONTROLLER, target);
			opponentPutCreature.parameters.put(EventType.Parameter.OBJECT, opponentCreatureCard);

			EventFactory opponentMayPutCreature = new EventFactory(EventType.PLAYER_MAY, "may put a creature card from among them onto the battlefield,");
			opponentMayPutCreature.parameters.put(EventType.Parameter.PLAYER, target);
			opponentMayPutCreature.parameters.put(EventType.Parameter.EVENT, Identity.instance(opponentPutCreature));
			this.addEffect(opponentMayPutCreature);

			this.addEffect(putIntoGraveyard(opponentTopThree, "then puts the rest into his or her graveyard."));

			SetGenerator yourTopThree = TopCards.instance(3, LibraryOf.instance(You.instance()));
			this.addEffect(reveal(yourTopThree, "You do the same with the top three cards of your library."));

			SetGenerator yourCreatureCard = Intersect.instance(HasType.instance(Type.CREATURE), yourTopThree);
			EventFactory youPutCreature = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from among them onto the battlefield");
			youPutCreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			youPutCreature.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			youPutCreature.parameters.put(EventType.Parameter.OBJECT, yourCreatureCard);

			EventFactory youMayPutCreature = new EventFactory(EventType.PLAYER_MAY, "");
			youMayPutCreature.parameters.put(EventType.Parameter.PLAYER, You.instance());
			youMayPutCreature.parameters.put(EventType.Parameter.EVENT, Identity.instance(youPutCreature));
			this.addEffect(youMayPutCreature);

			this.addEffect(putIntoGraveyard(yourTopThree, ""));

			SetGenerator result = Union.instance(EffectResult.instance(opponentPutCreature), EffectResult.instance(youPutCreature));
			SetGenerator thoseCreatures = NewObjectOf.instance(result);

			SetGenerator thereWereTwo = Intersect.instance(numberGenerator(2), Count.instance(thoseCreatures));
			EventFactory fight = fight(thoseCreatures, "Those creatures fight each other");
			this.addEffect(ifThen(thereWereTwo, fight, "If two creatures are put onto the battlefield this way, those creatures fight each other."));
		}
	}

	public GuildFeud(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, target opponent reveals the top
		// three cards of his or her library, may put a creature card from among
		// them onto the battlefield, then puts the rest into his or her
		// graveyard. You do the same with the top three cards of your library.
		// If two creatures are put onto the battlefield this way, those
		// creatures fight each other.
		this.addAbility(new GuildFeudAbility0(state));
	}
}
