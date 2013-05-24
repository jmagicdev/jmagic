package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Druidic Satchel")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({})
public final class DruidicSatchel extends Card
{
	public static final class DruidicSatchelAbility0 extends ActivatedAbility
	{
		public DruidicSatchelAbility0(GameState state)
		{
			super(state, "(2), (T): Reveal the top card of your library. If it's a creature card, put a 1/1 green Saproling creature token onto the battlefield. If it's a land card, put that card onto the battlefield under your control. If it's a noncreature, nonland card, you gain 2 life.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top card of your library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(reveal);

			SetGenerator thatCard = EffectResult.instance(reveal);
			SetGenerator itsACreature = Intersect.instance(thatCard, HasType.instance(Type.CREATURE));

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield");
			f.setColors(Color.GREEN);
			f.setSubTypes(SubType.SAPROLING);
			this.addEffect(ifThen(itsACreature, f.getEventFactory(), "If it's a creature card, put a 1/1 green Saproling creature token onto the battlefield."));

			SetGenerator itsALand = Intersect.instance(thatCard, HasType.instance(Type.LAND));

			EventFactory dropIt = putOntoBattlefield(thatCard, "Put that card onto the battlefield");
			this.addEffect(ifThen(itsALand, dropIt, "If it's a land card, put that card onto the battlefield under your control."));

			SetGenerator itsNeither = Not.instance(Union.instance(itsACreature, itsALand));

			this.addEffect(ifThen(itsNeither, gainLife(You.instance(), 2, "You gain 2 life"), "If it's a noncreature, nonland card, you gain 2 life."));
		}
	}

	public DruidicSatchel(GameState state)
	{
		super(state);

		// (2), (T): Reveal the top card of your library. If it's a creature
		// card, put a 1/1 green Saproling creature token onto the battlefield.
		// If it's a land card, put that card onto the battlefield under your
		// control. If it's a noncreature, nonland card, you gain 2 life.
		this.addAbility(new DruidicSatchelAbility0(state));
	}
}
