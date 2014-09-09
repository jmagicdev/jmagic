package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gloom Surgeon")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class GloomSurgeon extends Card
{
	public static final class GloomSurgeonAbility0 extends StaticAbility
	{
		public static final class GiselaPrevent extends DamageReplacementEffect
		{
			private GiselaPrevent(Game game, String name)
			{
				super(game, name);

				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment damage: damageAssignments)
				{
					if(damage.isCombatDamage && damage.takerID == thisObject.ID)
						ret.add(damage);
				}
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				int amount = damageAssignments.size();
				damageAssignments.clear();

				EventFactory mill = millCards(You.instance(), amount, "Exile that many cards from the top of your library.");

				return java.util.Collections.singletonList(mill);
			}
		}

		public GloomSurgeonAbility0(GameState state)
		{
			super(state, "If combat damage would be dealt to Gloom Surgeon, prevent that damage and exile that many cards from the top of your library.");

			this.addEffectPart(replacementEffectPart(new GiselaPrevent(this.game, "If combat damage would be dealt to Gloom Surgeon, prevent that damage and exile that many cards from the top of your library.")));
		}
	}

	public GloomSurgeon(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// If combat damage would be dealt to Gloom Surgeon, prevent that damage
		// and exile that many cards from the top of your library.
		this.addAbility(new GloomSurgeonAbility0(state));
	}
}
