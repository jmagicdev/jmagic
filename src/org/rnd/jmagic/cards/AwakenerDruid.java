package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Awakener Druid")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class AwakenerDruid extends Card
{
	public static final class Animate extends EventTriggeredAbility
	{
		public Animate(GameState state)
		{
			super(state, "When Awakener Druid enters the battlefield, target Forest becomes a 4/5 green Treefolk creature for as long as Awakener Druid remains on the battlefield. It's still a land.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(LandPermanents.instance(), HasSubType.instance(SubType.FOREST)), "target Forest");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator thisIsOnTheBattlefield = Intersect.instance(Permanents.instance(), thisCard);
			SetGenerator expiration = Not.instance(thisIsOnTheBattlefield);

			Animator animator = new Animator(targetedBy(target), 4, 5);
			animator.addColor(Color.GREEN);
			animator.addSubType(SubType.TREEFOLK);
			this.addEffect(createFloatingEffect(expiration, "Target Forest becomes a 4/5 green Treefolk creature for as long as Awakener Druid is on the battlefield. It's still a land.", animator.getParts()));
		}
	}

	public AwakenerDruid(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Animate(state));
	}
}
