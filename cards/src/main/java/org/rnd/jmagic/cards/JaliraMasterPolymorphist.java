package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jalira, Master Polymorphist")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class JaliraMasterPolymorphist extends Card
{
	public static final class JaliraMasterPolymorphistAbility0 extends ActivatedAbility
	{
		public JaliraMasterPolymorphistAbility0(GameState state)
		{
			super(state, "(2)(U), (T), Sacrifice another creature: Reveal cards from the top of your library until you reveal a nonlegendary creature card. Put that card onto the battlefield and the rest on the bottom of your library in a random order.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			SetGenerator library = LibraryOf.instance(You.instance());
			SetGenerator nonlegendaryCreature = RelativeComplement.instance(HasType.instance(Type.CREATURE), HasSuperType.instance(SuperType.LEGENDARY));
			SetGenerator cardsToReveal = TopMost.instance(library, numberGenerator(1), nonlegendaryCreature);
			this.addEffect(reveal(cardsToReveal, "Reveal cards from the top of your library until you reveal a nonlegendary creature card."));

			SetGenerator firstCreature = Intersect.instance(cardsToReveal, nonlegendaryCreature);
			this.addEffect(putOntoBattlefield(firstCreature, "Put that card onto the battlefield"));

			EventFactory tuck = new EventFactory(EventType.PUT_INTO_LIBRARY, "and the rest on the bottom of your library in a random order.");
			tuck.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tuck.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			tuck.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(cardsToReveal, firstCreature));
			tuck.parameters.put(EventType.Parameter.RANDOM, Empty.instance());
			this.addEffect(tuck);
		}
	}

	public JaliraMasterPolymorphist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(U), (T), Sacrifice another creature: Reveal cards from the top of
		// your library until you reveal a nonlegendary creature card. Put that
		// card onto the battlefield and the rest on the bottom of your library
		// in a random order.
		this.addAbility(new JaliraMasterPolymorphistAbility0(state));
	}
}
