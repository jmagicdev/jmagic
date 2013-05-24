package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Geistcatcher's Rig")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GeistcatchersRig extends Card
{
	public static final class GeistcatchersRigAbility0 extends EventTriggeredAbility
	{
		public GeistcatchersRigAbility0(GameState state)
		{
			super(state, "When Geistcatcher's Rig enters the battlefield, you may have it deal 4 damage to target creature with flying.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), hasFlying), "target creature with flying"));
			this.addEffect(youMay(permanentDealDamage(4, target, "Deal 4 damage to target creature with flying"), "You may have it deal 4 damage to target creature with flying."));
		}
	}

	public GeistcatchersRig(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// When Geistcatcher's Rig enters the battlefield, you may have it deal
		// 4 damage to target creature with flying.
		this.addAbility(new GeistcatchersRigAbility0(state));
	}
}
