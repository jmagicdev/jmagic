package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ogre Jailbreaker")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.ROGUE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class OgreJailbreaker extends Card
{
	public static final class OgreJailbreakerAbility1 extends StaticAbility
	{
		public OgreJailbreakerAbility1(GameState state)
		{
			super(state, "Ogre Jailbreaker can attack as though it didn't have defender as long as you control a Gate.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACK_AS_THOUGH_DOESNT_HAVE_DEFENDER);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			SetGenerator youControlAGate = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.GATE));
			this.canApply = Intersect.instance(this.canApply, youControlAGate);
		}
	}

	public OgreJailbreaker(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Ogre Jailbreaker can attack as though it didn't have defender as long
		// as you control a Gate.
		this.addAbility(new OgreJailbreakerAbility1(state));
	}
}
