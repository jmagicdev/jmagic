package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience.SifterFinal;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guild Feud")
@Types({Type.ENCHANTMENT})
@ManaCost("5R")
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
			SifterFinal opponentPutCreature = Sifter.start().player(target).reveal(3).drop(1, HasType.instance(Type.CREATURE)).dumpToGraveyard();
			this.addEffect(opponentPutCreature.getEventFactory("Target opponent reveals the top three cards of his or her library, may put a creature card from among them onto the battlefield, then puts the rest into his or her graveyard."));

			SifterFinal youPutCreature = Sifter.start().reveal(3).drop(1, HasType.instance(Type.CREATURE)).dumpToGraveyard();
			this.addEffect(youPutCreature.getEventFactory("You do the same with the top three cards of your library."));

			SetGenerator thoseCreatures = Union.instance(opponentPutCreature.newObject(), youPutCreature.newObject());

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
