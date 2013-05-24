package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class ShardsHerald extends Card
{
	public static final class Fetch extends ActivatedAbility
	{
		private final Color left;
		private final Color thisCreature;
		private final Color right;
		private final String heralding;

		public Fetch(GameState state, Color left, Color thisCreature, Color right, String heralding)
		{
			super(state, "(2)(" + thisCreature.getLetter() + "), (T), Sacrifice a " + left + " creature, a " + thisCreature + " creature, and a " + right + " creature: Search your library for a card named " + heralding + " and put it onto the battlefield. Then shuffle your library.");
			this.setManaCost(new ManaPool("(2)(" + thisCreature.getLetter() + ")"));
			this.costsTap = true;

			this.left = left;
			this.thisCreature = thisCreature;
			this.right = right;
			this.heralding = heralding;

			this.addCost(sacrifice(You.instance(), 1, Intersect.instance(CreaturePermanents.instance(), HasColor.instance(left)), "Sacrifice a " + left + " creature."));
			this.addCost(sacrifice(You.instance(), 1, Intersect.instance(CreaturePermanents.instance(), HasColor.instance(thisCreature)), "Sacrifice a " + thisCreature + " creature."));
			this.addCost(sacrifice(You.instance(), 1, Intersect.instance(CreaturePermanents.instance(), HasColor.instance(right)), "Sacrifice a " + right + " creature."));

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card named " + heralding + " and put it onto the battlefield. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(heralding)));
			this.addEffect(factory);
		}

		@Override
		public Fetch create(Game game)
		{
			return new Fetch(game.physicalState, this.left, this.thisCreature, this.right, this.heralding);
		}
	}

	public ShardsHerald(GameState state, Color left, Color thisCreature, Color right, String heralding)
	{
		super(state);

		this.addAbility(new Fetch(state, left, thisCreature, right, heralding));
	}
}
