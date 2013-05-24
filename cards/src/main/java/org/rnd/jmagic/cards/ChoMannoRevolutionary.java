package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Cho-Manno, Revolutionary")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.REBEL, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ChoMannoRevolutionary extends Card
{
	// Prevent all damage that would be dealt to Cho-Manno, Revolutionary.
	public static final class PreventAllDamage extends StaticAbility
	{
		public static final class PreventAllDamageEffect extends DamageReplacementEffect
		{
			public PreventAllDamageEffect(Game game, String name)
			{
				super(game, name);
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				GameObject thisObject = (GameObject)this.getStaticSourceObject(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == thisObject.ID)
						ret.add(assignment);
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}
		}

		public PreventAllDamage(GameState state)
		{
			super(state, "Prevent all damage that would be dealt to Cho-Manno, Revolutionary.");

			DamageReplacementEffect replacement = new PreventAllDamageEffect(this.game, "Prevent all damage that would be dealt to Cho-Manno, Revolutionary");

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public ChoMannoRevolutionary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new PreventAllDamage(state));
	}
}
