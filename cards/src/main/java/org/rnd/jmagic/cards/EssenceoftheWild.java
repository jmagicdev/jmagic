package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Essence of the Wild")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("3GGG")
@ColorIdentity({Color.GREEN})
public final class EssenceoftheWild extends Card
{
	public static final class CopyEssence extends ZoneChangeReplacementEffect
	{
		public CopyEssence(Game game)
		{
			super(game, "Creatures you control enter the battlefield as a copy of Essence of the Wild.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.CREATURE), You.instance(), false));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, NewObjectOf.instance(this.replacedByThis()));
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, This.instance());
			this.addEffect(createFloatingEffect(Empty.instance(), "Creatures you control enter the battlefield as a copy of Essence of the Wild.", part));
		}

		@Override
		public boolean isCloneEffect()
		{
			return true;
		}
	}

	public static final class EssenceoftheWildAbility0 extends StaticAbility
	{
		public EssenceoftheWildAbility0(GameState state)
		{
			super(state, "Creatures you control enter the battlefield as a copy of Essence of the Wild.");
			this.addEffectPart(replacementEffectPart(new CopyEssence(state.game)));
		}
	}

	public EssenceoftheWild(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Creatures you control enter the battlefield as a copy of Essence of
		// the Wild.
		this.addAbility(new EssenceoftheWildAbility0(state));
	}
}
