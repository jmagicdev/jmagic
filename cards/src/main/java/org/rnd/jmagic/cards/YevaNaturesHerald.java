package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yeva, Nature's Herald")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class YevaNaturesHerald extends Card
{
	public static final class YevaNaturesHeraldAbility1 extends StaticAbility
	{
		public YevaNaturesHeraldAbility1(GameState state)
		{
			super(state, "You may cast green creature cards as though they had flash.");

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(Cards.instance(), HasType.instance(Type.CREATURE), HasColor.instance(Color.GREEN)));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
			this.addEffectPart(part);
		}
	}

	public YevaNaturesHerald(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// You may cast green creature cards as though they had flash.
		this.addAbility(new YevaNaturesHeraldAbility1(state));
	}
}
