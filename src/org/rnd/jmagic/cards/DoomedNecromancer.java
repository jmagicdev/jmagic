package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Doomed Necromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN, SubType.MERCENARY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DoomedNecromancer extends Card
{
	public static final class Raise extends ActivatedAbility
	{
		public Raise(GameState state)
		{
			super(state, "(B), (T), Sacrifice Doomed Necromancer: Return target creature card from your graveyard to the battlefield.");

			this.setManaCost(new ManaPool("B"));

			this.costsTap = true;

			this.addCost(sacrificeThis("Doomed Necromancer"));

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card from your graveyard");

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.CONTROLLER, You.instance());
			moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, moveParameters, "Return target creature card from your graveyard to the battlefield."));
		}
	}

	public DoomedNecromancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Raise(state));
	}
}
