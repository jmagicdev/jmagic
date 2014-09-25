package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghostfire Blade")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class GhostfireBlade extends Card
{
	public static final class GhostfireBladeAbility0 extends StaticAbility
	{
		public GhostfireBladeAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +2));
		}
	}

	public static final class GhostfireBladeEquip extends SetGenerator
	{
		private GhostfireBladeEquip()
		{
			// singleton
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new GhostfireBladeEquip();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			return state.stack().objects.stream()//
			.filter(o -> //

			o instanceof org.rnd.jmagic.abilities.keywords.Equip.EquipAbility && //
			((ActivatedAbility)o).getSourceID() == thisObject.ID) //

			.collect(java.util.stream.Collectors.toCollection(Set::new));
		}
	}

	public static final class GhostfireBladeAbility2 extends StaticAbility
	{
		public GhostfireBladeAbility2(GameState state)
		{
			super(state, "Ghostfire Blade's equip ability costs (2) less to activate if it targets a colorless creature.");

			SetGenerator ability = Intersect.instance(GhostfireBladeEquip.instance(), HasTarget.instance(Colorless.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ability);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			this.addEffectPart(part);
		}
	}

	public GhostfireBlade(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2.
		this.addAbility(new GhostfireBladeAbility0(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));

		// Ghostfire Blade's equip ability costs (2) less to activate if it
		// targets a colorless creature.
		this.addAbility(new GhostfireBladeAbility2(state));
	}
}
