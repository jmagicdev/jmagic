package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Threaten")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Threaten extends Card
{
	public Threaten(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(untap(targetedBy(target), "Untap target creature"));

		ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		part1.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		part1.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		ContinuousEffect.Part part2 = addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class);

		this.addEffect(createFloatingEffect("and gain control of it until end of turn. That creature gains haste until end of turn.", part1, part2));
	}
}
