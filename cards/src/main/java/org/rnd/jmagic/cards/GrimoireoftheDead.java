package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grimoire of the Dead")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class GrimoireoftheDead extends Card
{
	public static final class GrimoireoftheDeadAbility0 extends ActivatedAbility
	{
		public GrimoireoftheDeadAbility0(GameState state)
		{
			super(state, "(1), (T), Discard a card: Put a study counter on Grimoire of the Dead.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			this.addEffect(putCounters(1, Counter.CounterType.STUDY, ABILITY_SOURCE_OF_THIS, "Put a study counter on Grimoire of the Dead"));
		}
	}

	public static final class GrimoireoftheDeadAbility1 extends ActivatedAbility
	{
		public GrimoireoftheDeadAbility1(GameState state)
		{
			super(state, "(T), Remove three study counters from Grimoire of the Dead and sacrifice it: Put all creature cards from all graveyards onto the battlefield under your control. They're black Zombies in addition to their other colors and types.");
			this.costsTap = true;

			// Remove three study counters from Grimoire of the Dead and
			// sacrifice it
			this.addCost(removeCounters(3, Counter.CounterType.STUDY, ABILITY_SOURCE_OF_THIS, "Remove three study counters from Grimoire of the Dead"));
			this.addCost(sacrificeThis("Grimoire of the Dead"));

			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator allCreatureCardsInGraveyards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inGraveyards);

			EventFactory everything = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put all creature cards from all graveyards onto the battlefield under your control.");
			everything.parameters.put(EventType.Parameter.CAUSE, This.instance());
			everything.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			everything.parameters.put(EventType.Parameter.OBJECT, allCreatureCardsInGraveyards);
			this.addEffect(everything);

			SetGenerator thoseCreatures = NewObjectOf.instance(EffectResult.instance(everything));

			ContinuousEffect.Part blackPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			blackPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thoseCreatures);
			blackPart.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));

			ContinuousEffect.Part zombiePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, thoseCreatures);
			zombiePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));

			this.addEffect(createFloatingEffect(Empty.instance(), "They're black Zombies in addition to their other colors and types.", blackPart, zombiePart));
		}
	}

	public GrimoireoftheDead(GameState state)
	{
		super(state);

		// (1), (T), Discard a card: Put a study counter on Grimoire of the
		// Dead.
		this.addAbility(new GrimoireoftheDeadAbility0(state));

		// (T), Remove three study counters from Grimoire of the Dead and
		// sacrifice it: Put all creature cards from all graveyards onto the
		// battlefield under your control. They're black Zombies in addition to
		// their other colors and types.
		this.addAbility(new GrimoireoftheDeadAbility1(state));
	}
}
