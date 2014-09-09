package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Haunted Fengraf")
@Types({Type.LAND})
@ColorIdentity({})
public final class HauntedFengraf extends Card
{
	public static final class HauntedFengrafAbility1 extends ActivatedAbility
	{
		public HauntedFengrafAbility1(GameState state)
		{
			super(state, "(3), (T), Sacrifice Haunted Fengraf: Return a creature card at random from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Haunted Fengraf"));

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator inGraveyard = Intersect.instance(InZone.instance(yourGraveyard), HasType.instance(Type.CREATURE));
			EventFactory random = random(inGraveyard, "Randomly identify a creature card in your graveyard");
			this.addEffect(random);

			EventFactory ret = new EventFactory(EventType.MOVE_OBJECTS, "Return that card from your graveyard to your hand");
			ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
			ret.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			ret.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(random));
			this.addEffect(ret);
		}
	}

	public HauntedFengraf(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (3), (T), Sacrifice Haunted Fengraf: Return a creature card at random
		// from your graveyard to your hand.
		this.addAbility(new HauntedFengrafAbility1(state));
	}
}
