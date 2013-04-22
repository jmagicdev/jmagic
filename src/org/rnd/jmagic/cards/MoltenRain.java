package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Molten Rain")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MoltenRain extends Card
{
	private static class WasNonbasic extends SetGenerator
	{
		private SetGenerator what;

		private WasNonbasic(SetGenerator what)
		{
			this.what = what;
		}

		public static WasNonbasic instance(SetGenerator what)
		{
			return new WasNonbasic(what);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
				if(!object.getSuperTypes().contains(SuperType.BASIC))
					return NonEmpty.set;

			return Empty.set;
		}
	}

	public MoltenRain(GameState state)
	{
		super(state);

		// Destroy target land.
		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));

		// If that land was nonbasic,
		SetGenerator wasNonbasic = WasNonbasic.instance(targetedBy(target));

		// Molten Rain deals 2 damage to the land's controller.
		EventFactory damage = spellDealDamage(2, ControllerOf.instance(targetedBy(target)), "Molten Rain deals 2 damage to the land's controller");

		EventType.ParameterMap ifParameters = new EventType.ParameterMap();
		ifParameters.put(EventType.Parameter.IF, wasNonbasic);
		ifParameters.put(EventType.Parameter.THEN, Identity.instance(damage));
		this.addEffect(new EventFactory(EventType.IF_CONDITION_THEN_ELSE, ifParameters, "If that land was nonbasic, Molten Rain deals 2 damage to the land's controller."));
	}
}
