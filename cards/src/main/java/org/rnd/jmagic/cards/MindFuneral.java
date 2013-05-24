package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Funeral")
@Types({Type.SORCERY})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class MindFuneral extends Card
{
	public static final class FourthFromTop extends SetGenerator
	{
		public static FourthFromTop instance(SetGenerator objects, SetGenerator zones)
		{
			return new FourthFromTop(objects, zones);
		}

		private SetGenerator objects;
		private SetGenerator zones;

		private FourthFromTop(SetGenerator objects, SetGenerator zones)
		{
			this.objects = objects;
			this.zones = zones;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();

			Set objects = this.objects.evaluate(state, thisObject);

			for(Zone zone: this.zones.evaluate(state, thisObject).getAll(Zone.class))
			{
				java.util.SortedMap<Integer, GameObject> tree = new java.util.TreeMap<Integer, GameObject>();

				for(int i = 0; i < zone.objects.size(); ++i)
				{
					GameObject object = zone.objects.get(i);
					if(objects.contains(object))
						tree.put(i, object);
				}

				if(tree.size() >= 4)
				{
					java.util.Iterator<GameObject> iter = tree.values().iterator();

					for(int i = 0; i < 3; ++i)
						iter.next();

					ret.add(iter.next());
				}
			}

			return ret;
		}
	}

	public MindFuneral(GameState state)
	{
		super(state);

		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

		SetGenerator opponentsLibrary = LibraryOf.instance(targetedBy(target));
		SetGenerator cardsToReveal = TopCards.instance(IndexOf.instance(FourthFromTop.instance(HasType.instance(Type.LAND), opponentsLibrary)), opponentsLibrary);

		EventFactory revealFactory = new EventFactory(EventType.REVEAL, "Target opponent reveals cards from the top of his or her library until four land cards are revealed.");
		revealFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		revealFactory.parameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(revealFactory);

		EventFactory millFactory = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "That player puts all cards revealed this way into his or her graveyard.");
		millFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		millFactory.parameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(millFactory);
	}
}
