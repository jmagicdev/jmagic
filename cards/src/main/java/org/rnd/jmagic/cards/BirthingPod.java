package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Birthing Pod")
@Types({Type.ARTIFACT})
@ManaCost("3(G/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BirthingPod extends Card
{
	public static final class BirthingPodAbility1 extends ActivatedAbility
	{
		public BirthingPodAbility1(GameState state)
		{
			super(state, "(1)(G/P), (T), Sacrifice a creature: Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.");
			this.setManaCost(new ManaPool("(1)(G/P)"));
			this.costsTap = true;

			EventFactory cost = sacrificeACreature();
			this.addCost(cost);

			SetGenerator inLibrary = InZone.instance(LibraryOf.instance(You.instance()));
			SetGenerator sacrificedCreature = OldObjectOf.instance(CostResult.instance(cost));
			SetGenerator hasCMCplusOne = HasConvertedManaCost.instance(Sum.instance(Union.instance(ConvertedManaCostOf.instance(sacrificedCreature), numberGenerator(1))));
			SetGenerator filter = Intersect.instance(inLibrary, HasType.instance(Type.CREATURE), hasCMCplusOne);

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card onto the battlefield, then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(filter));
			this.addEffect(factory);

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public BirthingPod(GameState state)
	{
		super(state);

		// (1)((g/p)), (T), Sacrifice a creature: Search your library for a
		// creature card with converted mana cost equal to 1 plus the sacrificed
		// creature's converted mana cost, put that card onto the battlefield,
		// then shuffle your library. Activate this ability only any time you
		// could cast a sorcery.
		this.addAbility(new BirthingPodAbility1(state));
	}
}
