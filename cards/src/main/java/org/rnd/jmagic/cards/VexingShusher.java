package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Vexing Shusher")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("(RG)(RG)")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class VexingShusher extends Card
{
	public static final class BeQuiet extends ActivatedAbility
	{
		public BeQuiet(GameState state)
		{
			super(state, "(RG): Target spell can't be countered by spells or abilities.");
			this.setManaCost(new ManaPool("(RG)"));

			Target target = this.addTarget(Spells.instance(), "target spell");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.COUNTER);
			pattern.put(EventType.Parameter.CAUSE, spellsAndAbilities());
			pattern.put(EventType.Parameter.OBJECT, targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));

			this.addEffect(createFloatingEffect("Target spell can't be countered by spells or abilities.", part));
		}
	}

	public VexingShusher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Vexing Shusher can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// ((r/g)): Target spell can't be countered by spells or abilities.
		this.addAbility(new BeQuiet(state));
	}
}
