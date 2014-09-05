package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Skyblinder Staff")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SkyblinderStaff extends Card
{
	public static final class SkyblinderStaffAbility0 extends StaticAbility
	{
		public SkyblinderStaffAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0 and can't be blocked by creatures with flying.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +1, +0));

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator illegalBlock = Intersect.instance(Blocking.instance(equippedCreature), CreaturePermanents.instance(), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}

	public SkyblinderStaff(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0 and can't be blocked by creatures with
		// flying.
		this.addAbility(new SkyblinderStaffAbility0(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
