package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Unholy Grotto")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class UnholyGrotto extends Card
{
	public static final class UnholyGrottoAbility1 extends ActivatedAbility
	{
		public UnholyGrottoAbility1(GameState state)
		{
			super(state, "(B), (T): Put target Zombie card from your graveyard on top of your library.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ZOMBIE), InZone.instance(GraveyardOf.instance(You.instance()))), "target Zombie card from your graveyard");

			EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target Zombie card from your graveyard on top of your library.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(move);
		}
	}

	public UnholyGrotto(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (B), (T): Put target Zombie card from your graveyard on top of your
		// library.
		this.addAbility(new UnholyGrottoAbility1(state));
	}
}
